package edu.stevens.cs548.clinic.domain;

import java.util.Date;

public class PatientFactory implements IPatientFactory {

	@Override
	public Patient createPatient(long pid, String name, Date dob,int age) {
		// TODO Auto-generated method stub
		Patient p=new Patient();
		p.setPatientId(pid);
		p.setName(name);
		p.setDob(dob);
		p.setAge(age);
		return p;
	}

}
