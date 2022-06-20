package com.kodlamaio.rentACar.core.utilities.adapters;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.PersonCheckService;
import com.kodlamaio.rentACar.business.request.users.CreateUserRequest;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;
@Service
public class MernisServiceAdapter implements PersonCheckService {

	@Override
	public boolean CheckIfCorrectPerson(CreateUserRequest createUserRequest) throws NumberFormatException, RemoteException {
		KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();
		return kpsPublicSoapProxy.TCKimlikNoDogrula(Long.parseLong(createUserRequest.getIdentityNumber()),
				createUserRequest.getFirstName().toUpperCase(), createUserRequest.getLastName().toUpperCase(),
				createUserRequest.getBirthYear());

	
	}

}
