package com.micro;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.is;


import com.micro.actions.PurchaseActions;
import com.micro.controllers.RequestPurchase;
import com.micro.data.DAOFactory;
import com.micro.mocking.PurchaseDaoMemory;
import com.micro.mocking.PurchaseDetailsDaoMemory;
import com.micro.models.Purchase;
import com.micro.models.PurchaseDetails;

import de.svenjacobs.loremipsum.LoremIpsum;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MicroPurchasingSystemApplication.class)
public class MicroPurchasingSystemApplicationTests {

    /**
     * the number of entries that we will generate for testing purposes
     */
    public static final int N_DB_ENTRIES = 10000;
    
    /**
     * This is the media type that will be used in the requests performed
     * by the mockMvc object
     */
    private MediaType contentType = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(),
        Charset.forName("utf8")
    );

    /**
     * will be used to perform the requests to the server
     */
    private MockMvc mockMvc;
    
    /**
     * will be used to do JSON transformations
     */
    @SuppressWarnings("rawtypes")
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * utility library to generate lorem ipsum examples
     */
    private LoremIpsum loremIpsum;
    
    /**
     * the in memory store in use where purchases are stored
     */
    private PurchaseDaoMemory purchaseStore;
    /**
     * the in memory store where purchase details are stored
     */
    private PurchaseDetailsDaoMemory purchaseDetailsStore;
    
    /**
     * initialises the test suite
     */
    public MicroPurchasingSystemApplicationTests() {
        PurchaseActions.initDaoObjects(DAOFactory.IN_MEMORY_DAO);
        this.loremIpsum = new LoremIpsum();
        this.purchaseStore = (PurchaseDaoMemory)PurchaseActions.getPurchaseDao();
        this.purchaseDetailsStore = (PurchaseDetailsDaoMemory)PurchaseActions.getPurchaseDetailsDao();
    }
    
    /**
     * utility function to create a product type
     * product types will have the shape: TYPE_[A-Z]
     * @return
     */
    private static String createProductType() {
        return "TYPE_" + Character.toString(
            (char) ThreadLocalRandom.current().nextInt(65, 91)
        );
    }
    
    /**
     * creates a new expires
     * for the generation of this value we get a random between
     * 1 and 5 that represents the numbers of days and we add it to the
     * current time
     * @return
     */
    private static final long createExpires() {
        int nDays = ThreadLocalRandom.current().nextInt(1, 5);
        return System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(nDays, TimeUnit.DAYS);
    }
    
    /**
     * creates a quantity between 1 and 100
     * @return
     */
    private static final int createQuantity() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }
    
    /**
     * creates a value between 0 and 1000
     * @return
     */
    private static final double createValue() {
        return ThreadLocalRandom.current().nextDouble(1, 1000);
    }

    /**
     * utility function to get a random purchase from the store
     * @return
     */
    private Purchase pickRandomPurchase() {
        List<Long> keys = new ArrayList<Long>(this.purchaseStore.getStore().keySet());
        long key = keys.get(ThreadLocalRandom.current().nextInt(keys.size()));
        return this.purchaseStore.getStore().get(key);
    }

    /** 
     * sets up the test suite
     */
    public void setup() {
        // creating the mockMcv object
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        
        // emptying the stores
        this.purchaseStore.getStore().clear();
        this.purchaseDetailsStore.getStore().clear();
        
        // creating new values for the two stores
        for (int i=0; i < N_DB_ENTRIES; i++) {
            String productType =  createProductType();
            long purchaseId = this.purchaseStore.generateObjectId();
            long detailsId = this.purchaseDetailsStore.generateObjectId();
            long expires = createExpires();
            String description = this.loremIpsum.getWords(50);
            int quantity = createQuantity();
            double value = createValue();
            
            Purchase purchase = new Purchase(purchaseId, productType, expires, detailsId);
            PurchaseDetails details = new PurchaseDetails(description, quantity, value);
            
            this.purchaseStore.getStore().put(purchaseId, purchase);
            this.purchaseDetailsStore.getStore().put(detailsId,  details);
        }
    }
    
    /**
     * Tests that getting an non existent purchase returns a 404 code
     * form the server
     * @throws Exception
     */
    @Test
    public void purchaseNotFound() throws Exception {
        int invalidId = N_DB_ENTRIES * 3;
        mockMvc.perform(get("/purchases/" + invalidId).contentType(contentType)).andExpect(status().isNotFound());
    }
    
    /**
     * tests that getting an existent purchase returns a 200 code
     * and the data matches what we have in the store
     * @throws Exception
     */
    @Test
    public void purchaseFound() throws Exception  {        
        Purchase purchase = this.pickRandomPurchase();
        PurchaseDetails details = this.purchaseDetailsStore.getStore().get(purchase.getPurchaseDetailsId());
        mockMvc.perform(get("/purchases/" + purchase.getId()).contentType(contentType))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$.id", is(purchase.getId())))
               .andExpect(jsonPath("$.productType", is(purchase.getProductType())))
               .andExpect(jsonPath("$.expires", is(purchase.getExpires())))
               .andExpect(jsonPath("$.details.description", is(details.getDescription())))
               .andExpect(jsonPath("$.details.quantity", is(details.getQuantity())))
               .andExpect(jsonPath("$.details.value", is(details.getValue())))
               .andExpect(jsonPath("$.details.id", is(details.getId())));
        
    }
    
    /**
     * tests that getting all the valid purchases returns a 200 code
     * 
     * TODO: compare with the data on the store
     * @throws Exception
     */
    @Test
    public void readValidPurchases() throws Exception  {
        mockMvc.perform(get("/purchases/").contentType(contentType))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType));
    }
    
    /**
     * tests that creating a new purchase returns a 200 code
     * and that something was insert to the store
     * 
     * @throws Exception
     */
    @Test
    public void createPurchase() throws Exception  {
        RequestPurchase prequest = new RequestPurchase();
        prequest.setProductType(createProductType());
        prequest.setExpires(createExpires());
        prequest.setDescription(this.loremIpsum.getWords(50));
        prequest.setQuantity(createQuantity());
        prequest.setValue(createValue());
        String prequestJson = json(prequest);
        int nPurchasesBefore = this.purchaseStore.getStore().size();
        int nPurchaseDetailsBefore = this.purchaseDetailsStore.getStore().size();
        mockMvc.perform(post("/purchases/").contentType(contentType).content(prequestJson))
               .andExpect(status().isOk());
        int nPurchasesAfter = this.purchaseStore.getStore().size();
        int nPurchaseDetailsAfter = this.purchaseDetailsStore.getStore().size();
        Assert.assertTrue(nPurchaseDetailsAfter > nPurchaseDetailsBefore);
        Assert.assertTrue(nPurchasesAfter > nPurchasesBefore);
    }
    
    /**
     * tests that it is not possible to create a purchase
     * without all the fields
     * 
     * server must return a 4XX code
     * 
     * @throws Exception
     */
    @Test
    public void createInvalidPurchase() throws Exception  {
        RequestPurchase prequest = new RequestPurchase();
        prequest.setProductType(createProductType());
        prequest.setDescription(this.loremIpsum.getWords(50));
        prequest.setQuantity(createQuantity());
        prequest.setValue(createValue());
        String prequestJson = json(prequest);
        mockMvc.perform(post("/purchases/").contentType(contentType).content(prequestJson))
               .andExpect(status().is4xxClientError());
        
    }
    /**
     * tests that modifying a purchase works and the server
     * returns a 200 code
     * 
     * it also checks that the new value was persisted
     * @throws Exception
     */
    @Test
    public void modifyPurchase()  throws Exception {
        Purchase purchase = this.pickRandomPurchase();
        RequestPurchase prequest = new RequestPurchase();
        prequest.setId(purchase.getId());
        String newDescription = this.loremIpsum.getWords(100);
        prequest.setDescription(newDescription);
        String prequestJson = json(prequest);
        mockMvc.perform(put("/purchases/" + purchase.getId()).contentType(contentType).content(prequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.details.description", is(newDescription)));
        Assert.assertTrue(
            newDescription.equals(
                this.purchaseDetailsStore.getStore().get(
                    purchase.getPurchaseDetailsId()
                ).getDescription())
        );
    }
    
    /**
     * tests that modifying a non existent purchase is not possible.
     * the server must return a not found error code
     * @throws Exception
     */
    @Test
    public void modifyNonExistentPurchase() throws Exception  {
        RequestPurchase prequest = new RequestPurchase();
        int invalidId = N_DB_ENTRIES * 3;
        prequest.setId(invalidId);
        String prequestJson = json(prequest);
        mockMvc.perform(put("/purchases/" + invalidId).contentType(contentType).content(prequestJson))
               .andExpect(status().isNotFound());
    }
    
    /**
     * tests that it is possible to remove a purchase
     * 
     * server must return a 200 code and the purchase must no longer
     * be present in the database
     * 
     * @throws Exception
     */
    @Test
    public void removePurchase() throws Exception  {
        Purchase purchase = this.pickRandomPurchase();
        RequestPurchase prequest = new RequestPurchase();
        prequest.setId(purchase.getId());
        String prequestJson = json(prequest);
        mockMvc.perform(delete("/purchases/" + purchase.getId()).contentType(contentType).content(prequestJson))
               .andExpect(status().isOk());
        Assert.assertTrue(this.purchaseStore.getStore().containsKey(purchase.getId()) == false);
    }

    /**
     * tests that trying to remove a non existent purchase is not
     * possible. the server must return a 404 error code.
     * @throws Exception
     */
    @Test
    public void removeNonExistentPurchase() throws Exception  {
        RequestPurchase prequest = new RequestPurchase();
        int invalidId = N_DB_ENTRIES * 3;
        prequest.setId(invalidId);
        String prequestJson = json(prequest);
        mockMvc.perform(delete("/purchases/" + invalidId).contentType(contentType).content(prequestJson))
               .andExpect(status().isOk());
    }
    

    /**
     * converts an object into the json format
     * @param o
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
            o,
            MediaType.APPLICATION_JSON,
            mockHttpOutputMessage
        );
        return mockHttpOutputMessage.getBodyAsString();
    }
    
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
}
