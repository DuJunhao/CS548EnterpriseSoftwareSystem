package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.DAO.IPatientDAO;
import edu.stevens.cs548.clinic.domain.DAO.IProviderDAO;

public interface IClinicGateway {
	public IPatientFactory getPatientFactory(); 
	public IPatientDAO getPatientDAO();
	public IProviderFactory getProviderFactory();
	public IProviderDAO getProviderDAO();
}
