# Parking Lot

- Install kotlin complier:
```shell
brew install kotlin 
```

- Compile the code:

```shell
cd src/main/kotlin/co/practices/parkinglot
kotlinc . -include-runtime -d build/parking-lot.jar
```

- Run the console application:
```shell
java -jar build/parking-lot.jar 
```

- The output screen:
```shell
[Parking Lot]
=============
- create [size] - Create parking lot of size N
- park [car-number] - Parks a car
- leave [car-number] [hours] - Removes (unpack) a car
- status - Print status of the parking lot
- exit - Exit the program
- help - Show help
=============
Please enter a valid command, use exit to exit the program or help to show available commands
```