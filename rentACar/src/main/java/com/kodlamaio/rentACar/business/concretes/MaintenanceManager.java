package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.request.maintenances.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.request.maintenances.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.business.request.maintenances.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.business.responses.maintenances.ListMaintenanceResponse;
import com.kodlamaio.rentACar.business.responses.maintenances.MaintenanceResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.MaintenanceRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Maintenance;
@Service
public class MaintenanceManager implements MaintenanceService {
	MaintenanceRepository maintenanceRepository;
	CarRepository carRepository;
	ModelMapperService modelMapperService;
	
	@Autowired
	public MaintenanceManager(MaintenanceRepository maintenanceRepository, ModelMapperService modelMapperService,CarRepository carRepository) {
		this.maintenanceRepository = maintenanceRepository;
		this.modelMapperService = modelMapperService;
		this.carRepository = carRepository;
	}

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
		
		checkCarUnderMainteance(createMaintenanceRequest.getCarId());
		checkDateToMaintenance(createMaintenanceRequest.getSentDate(), createMaintenanceRequest.getReturnedDate());
		checkCarIdFromMaintenance(createMaintenanceRequest.getCarId());
		Maintenance maintenance = this.modelMapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);
		Car car = this.carRepository.findById(createMaintenanceRequest.getCarId());	
		
		car.setId(createMaintenanceRequest.getId());
		car.setState(2);	
		
		this.maintenanceRepository.save(maintenance);
		return new SuccessResult("MAINTENANCE.SAVED");
	}

	@Override
	public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		checkIsMaintenanceExists(deleteMaintenanceRequest.getId());
		int carId = deleteMaintenanceRequest.getId();
		this.maintenanceRepository.deleteById(carId);
		return new SuccessResult("MAINTENANCE DELETED");		
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
		checkIsMaintenanceExists(updateMaintenanceRequest.getId());
		Maintenance oldMaintenance = this.maintenanceRepository.findById(updateMaintenanceRequest.getId());
		Car car = this.carRepository.findById(updateMaintenanceRequest.getCarId());
		car.setState(2);
		checkCarChangeInUpdate(updateMaintenanceRequest.getId(), oldMaintenance.getId());
		Maintenance maintenanceToUpdate = this.modelMapperService.forRequest().map(updateMaintenanceRequest, Maintenance.class);
		this.maintenanceRepository.save(maintenanceToUpdate);
		return new SuccessResult("MAINTENANCE UPDATED");
	}
	
	

	@Override
	public DataResult<List<ListMaintenanceResponse>> getAll() {
		List<Maintenance> maintenances = this.maintenanceRepository.findAll();
		List<ListMaintenanceResponse> response = maintenances.stream().map(maintenance -> this.modelMapperService.forResponse()
				.map(maintenances, ListMaintenanceResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListMaintenanceResponse>>(response,"MAİNTENANCES.GETTED");

	}

	@Override
	public DataResult<MaintenanceResponse> getById(int id) {
		Maintenance maintenance = this.maintenanceRepository.findById(id);
		MaintenanceResponse response = this.modelMapperService.forResponse().map(maintenance, MaintenanceResponse.class);

		return new SuccessDataResult<MaintenanceResponse>(response,"MAINTENANCE.GETTED");
		
	}
	private void checkIsMaintenanceExists(int id) {
		Maintenance maintenance = this.maintenanceRepository.findById(id);
		if(maintenance==null) {
			throw new BusinessException("THERE IS NOT MAINTENANCE");
		}
	}
	
	private void checkCarUnderMainteance(int carId) {
		Car car = this.carRepository.findById(carId);
		if(car.getState()==2) {
			throw new BusinessException("CAR IS ALDREADY IN MAINTENANCE");
		}
	}
	
	private void checkCarIdFromMaintenance(int carId) {
		Car car = this.carRepository.findById(carId);
		if (car == null) {
			throw new BusinessException("THIS CAR IS NOT IN CAR REPOSITORY");
		}
	}
	
	private void checkDateToMaintenance(LocalDate sentDate, LocalDate returnedDate) {
		if(!sentDate.isBefore(returnedDate)||sentDate.isBefore(LocalDate.now())) {
			throw new BusinessException("SENT AND RETURNED DATE ERROR");
		}
	}
	
	private void checkCarChangeInUpdate(int newMaintenanceId, int oldMaintenanceId) {
		Maintenance newMaintenance =this.maintenanceRepository.findById(newMaintenanceId);
		Maintenance oldMaintenance =this.maintenanceRepository.findById(oldMaintenanceId);
		
		if(newMaintenance.getCar().getId() != oldMaintenance.getCar().getId()) {
			Car car = this.carRepository.findById(oldMaintenance.getCar().getId());
			car.setState(1);
			this.carRepository.save(car);
		}
	}
}
