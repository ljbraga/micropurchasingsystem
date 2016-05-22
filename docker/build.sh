#!/bin/bash
cp ../source/MicroPurchasingSystem/target/micropurchasingsys-0.0.1-SNAPSHOT.jar micropurchasesys.jar
docker build -t ljbraga/micropurchasesys:1.0 .
