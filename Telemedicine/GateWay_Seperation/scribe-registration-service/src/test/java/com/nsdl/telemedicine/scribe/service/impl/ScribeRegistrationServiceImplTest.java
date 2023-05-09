/**
 * 
 */
package com.nsdl.telemedicine.scribe.service.impl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.nsdl.telemedicine.scribe.dto.ScribeRegDTO;
import com.nsdl.telemedicine.scribe.dto.UserDTO;
import com.nsdl.telemedicine.scribe.jpa.repository.AuditScribeRegistrationJpaRepository;
import com.nsdl.telemedicine.scribe.jpa.repository.DoctorMasterDetailsJpaRepo;
import com.nsdl.telemedicine.scribe.jpa.repository.ScribeRegistrationRepositoryJpa;

/**
 * @author Pegasus_girishk
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ScribeRegistrationServiceImpl.class })
public class ScribeRegistrationServiceImplTest {
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@InjectMocks
	private ScribeRegistrationServiceImpl scribeRegistrationServiceImpl;
	
	@Mock
	private ScribeRegistrationRepositoryJpa scribeRegistrationJpaRepo;

	@Mock
	private DoctorMasterDetailsJpaRepo doctorMasterDetailsJpaRepo;

	@Mock
	private AuditScribeRegistrationJpaRepository auditScribeRegJpaRepository;

	UserDTO userDetails = new UserDTO();
	
	ScribeRegDTO scribeRegDTO = new ScribeRegDTO();
	
	@Before
	public void setup() {
		userDetails.setUserName("PATIENT123");
		ReflectionTestUtils.setField(scribeRegistrationServiceImpl, "userDetails",userDetails);
		
		scribeRegDTO.setScrbFullName("SCRIBEUSER");
		scribeRegDTO.setScrbMobNo("9876565656");
		scribeRegDTO.setScrbUserID("SCRIBEUSER");
		scribeRegDTO.setScrbEmail("scribe12@gmail.com");
		scribeRegDTO.setScrbdrUserIDfk("GIRISHDOC");
		scribeRegDTO.setScrbAdd1("Pune");
		scribeRegDTO.setScrbAdd2("Pune");
		scribeRegDTO.setScrbAdd3("Pune");
		scribeRegDTO.setScrbAdd4("Pune");
		scribeRegDTO.setScribeProfilePhoto("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RCiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAQAAAISodpAAQAAAABAAAI");
	}
}
