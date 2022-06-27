package com.kodlamaio.rentACar.core.utilities.adapters.concretes;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.request.individualCustomers.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.users.CreateUserRequest;
import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.PersonCheckService;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;
@Service
public class MernisServiceAdapter implements PersonCheckService {

	@Override
	public boolean CheckIfCorrectPerson(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();
		return kpsPublicSoapProxy.TCKimlikNoDogrula(Long.parseLong(createIndividualCustomerRequest.getIdentityNumber()),
				createIndividualCustomerRequest.getFirstName().toUpperCase(), createIndividualCustomerRequest.getLastName().toUpperCase(),
				createIndividualCustomerRequest.getBirthYear());

	
	}
	
	public boolean CheckIfCorrectPerson(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();
		return kpsPublicSoapProxy.TCKimlikNoDogrula(Long.parseLong(updateIndividualCustomerRequest.getIdentityNumber()),
				updateIndividualCustomerRequest.getFirstName().toUpperCase(), updateIndividualCustomerRequest.getLastName().toUpperCase(),
				updateIndividualCustomerRequest.getBirthYear());

}
}
