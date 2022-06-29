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
		Brand brand =  this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		this.brandRepository.save(brand);
		return new SuccessResult("BRAND ADDED");
	}
	
	
	
	
	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		checkIsBrandNull(deleteBrandRequest.getId());
		int brandId = deleteBrandRequest.getId();
		this.brandRepository.deleteById(brandId);
		return new SuccessResult("BRAND DELETED");
	}
	
	
	

	

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		checkIsBrandNull(updateBrandRequest.getId());
		checkIfExistByName(updateBrandRequest.getName());
		Brand brandToUpdate = modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
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

	@Override
	public DataResult<BrandResponse> getById( int id){
		checkIsBrandNull(id);
		Brand brand  =  brandRepository.findById(id);
		BrandResponse response = this.modelMapperService.forResponse().map(brand, BrandResponse.class);
		return new SuccessDataResult<BrandResponse>(response,"BRAND.GETTED"); 
		
	}
	
	private void checkIfExistByName(String name) {
		Brand currentBrand = this.brandRepository.findByName(name);
		if(currentBrand!=null ) {
			throw new BusinessException("BRAND EXISTS");
		}
		}

	private void checkIsBrandNull(int brandId) {
		Brand brand = this.brandRepository.findById(brandId);
		if(brand==null) {
			throw new BusinessException("THERE IS NOT BRAND");
		}
	}
}


		
		

		
		
	


