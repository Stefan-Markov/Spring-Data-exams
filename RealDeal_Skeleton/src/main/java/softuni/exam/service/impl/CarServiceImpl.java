package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.gson.CarImportDto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {
    private final static String CARS_PATH = "src/main/resources/files/json/cars.json";
    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(CARS_PATH)));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();

        CarImportDto[] carImportDtos = this.gson.fromJson(this.readCarsFileContent(), CarImportDto[].class);
        for (CarImportDto carImportDto : carImportDtos) {
            Car car = this.modelMapper.map(carImportDto, Car.class);
            if (this.validationUtil.isValid(carImportDto)) {
                sb.append(String.format("Successfully imported car - %s %s%n", carImportDto.getMake(),
                        carImportDto.getModel()));
                this.carRepository.saveAndFlush(car);
            } else {
                sb.append("Invalid car%n");
            }
        }
        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();
        Set<Car> cars = this.carRepository.exportCars();

        for (Car car : cars) {
            sb.append(String.format("Car make - %s, model - %s", car.getMake(), car.getModel())).append(System.lineSeparator());
            sb.append(String.format("\tKilometers - %d", car.getKilometers())).append(System.lineSeparator());
            sb.append(String.format("\tRegistered on - %s", car.getRegisteredOn())).append(System.lineSeparator());
            sb.append(String.format("\tNumber of pictures - %d", car.getPictures().size())).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
