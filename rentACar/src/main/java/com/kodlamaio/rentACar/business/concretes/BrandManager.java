package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.BrandService;
import com.kodlamaio.rentACar.business.request.brands.CreateBrandRequest;
import com.kodlamaio.rentACar.business.request.brands.DeleteBrandRequest;
import com.kodlamaio.rentACar.business.request.brands.UpdateBrandRequest;
import com.kodlamaio.rentACar.business.responses.brands.BrandResponse;
import com.kodlamaio.rentACar.business.responses.brands.ListBrandResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.BrandRepository;
import com.kodlamaio.rentACar.entities.concretes.Brand;

//BrandServiceImpl Impl görünce Manager aklına gelsin
@Service
public class BrandManager implements BrandService {

	private BrandRepository brandRepository;
	private ModelMapperService modelMapperService;
	
	@Autowired   
	public BrandManager(BrandRepository brandRepository, ModelMapperService modelMapperService) {
		this.brandRepository=brandRepository;
		this.modelMapperService = modelMapperService;
	}
	
	

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
		checkIfExistByName(createBrandRequest.getName());
	
//		Brand brand = new Brand();
		Brand brand =  this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		
//		brand.setName(createBrandRequest.getName());
		this.brandRepository.save(brand);
		return new SuccessResult("BRAND ADDED");
	}
	
	private void checkIfExistByName(String name) {
	Brand currentBrand = this.brandRepository.findByName(name);
	if(currentBrand!=null ) {
		throw new BusinessException("BRAND EXISTS");
	}
	}
	//Business Exception girince metod dan mesajı atar
	
	
	
	
	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		int brandId = deleteBrandRequest.getId();
		this.brandRepository.deleteById(brandId);
		return new SuccessResult("BRAND DELETED");
	}

	

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		Brand brandToUpdate = modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
//		Brand brandToUpdate = brandRepository.findById(updateBrandRequest.getId());
//		brandToUpdate.setName(updateBrandRequest.getName());   modelMApper varsa setlemeye gerek yok
		this.brandRepository.save(brandToUpdate);
		return new SuccessResult("BRAND UPDATED");
		
	}
	
	@Override
	public DataResult<List<ListBrandResponse>> getAll(){
		List<Brand> brands = this.brandRepository.findAll();
		
		List<ListBrandResponse> response  = brands.stream().map(brand->this.modelMapperService.forResponse()
				.map(brand,ListBrandResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListBrandResponse>>(response);
		
		
	} 

//	@Override
//	public DataResult<List<Brand>> getAll() {
//		
//		return new SuccessDataResult<List<Brand>>(this.brandRepository.findAll());
//	}

//	@Override
//	public DataResult<Brand> getById(int id) {
//		Brand brand = new Brand();
//		for (Brand item : brandRepository.findAll()) {
//			if (item.getId() == id) {
//				brand = item;
//			}
//		}
//		return new SuccessDataResult<Brand>(brand);
//	}
//

	@Override
	public DataResult<BrandResponse> getById( int id){
		Brand brand  =  brandRepository.findById(id);
		BrandResponse response = this.modelMapperService.forResponse().map(brand, BrandResponse.class);
		return new SuccessDataResult<BrandResponse>(response,"BRAND.GETTED"); 
		
	}






}


		
		

		
		
	


