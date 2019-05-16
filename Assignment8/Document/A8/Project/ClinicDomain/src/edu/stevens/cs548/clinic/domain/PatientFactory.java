package edu.stevens.cs548.clinic.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PatientFactory implements IPatientFactory {

	@Override
	public Patient createPatient(long pid, String name, Date dob, int age) throws IPatientDAO.PatientExn {
		if (age != computeAge(dob)) {
			throw new IPatientDAO.PatientExn("Age and date of birth do not match." + age + "!=" + computeAge(dob));
		} else {
			Patient p = new Patient();
			p.setPatientId(pid);
			p.setName(name);
			p.setBirthDate(dob);
			p.setAge(age);
			return p;
		}
	}
	
	public static int computeAge(Date dob) {
		int age = 0;
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dnow = formatter.format(currentTime);
		String dateofBirth = formatter.format(dob);
		String[] birthday = dateofBirth.split("-");
		String[] now = dnow.split("-");
		if (Integer.parseInt(birthday[1]) < Integer.parseInt(now[1]))
			age = Integer.parseInt(now[0]) - Integer.parseInt(birthday[0]);
		else if (Integer.parseInt(birthday[1]) == Integer.parseInt(now[1])
				&& Integer.parseInt(birthday[2]) > Integer.parseInt(now[2]))
			age = Integer.parseInt(now[0]) - Integer.parseInt(birthday[0]) - 1;
		else if (Integer.parseInt(birthday[1]) == Integer.parseInt(now[1])
				&& Integer.parseInt(birthday[2]) < Integer.parseInt(now[2]))
			age = Integer.parseInt(now[0]) - Integer.parseInt(birthday[0]);
		else
			age = Integer.parseInt(now[0]) - Integer.parseInt(birthday[0]) - 1;
		return age;
	}

}
