package projets.safetynet.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projets.safetynet.model.core.Person;

@SpringBootTest
public class PersonDaoTest {
	
	@Autowired
	private PersonDao dao;
	
	private Person p1 = new Person ("firstName1","lastName1","address1","city1",11111,"phone1","email1");
	private Person p2 = new Person ("firstName2","lastName2","address2","city2",22222,"phone2","email2");
	private Person p3 = new Person ("firstName3","lastName3","address3","city3",33333,"phone3","email3");
	
	@BeforeEach
	private void prepareTests()
	{
		dao.set(new ArrayList<Person>());
	}
	
	@Test
	void givenTestData_getAll_returnsCompleteList()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2, p3));
		dao.set(listGiven);
		// WHEN
		ArrayList<Person> listResult = dao.getAll();
		// THEN
		assertEquals(3, listResult.size());
		assertEquals("firstName1", listResult.get(0).getFirstName());
		assertEquals("lastName1", listResult.get(0).getLastName());
		assertEquals("address1", listResult.get(0).getAddress());
		assertEquals("city1", listResult.get(0).getCity());
		assertEquals(11111, listResult.get(0).getZip());
		assertEquals("phone1", listResult.get(0).getPhone());
		assertEquals("email1", listResult.get(0).getEmail());
		assertEquals("firstName2", listResult.get(1).getFirstName());
		assertEquals("lastName2", listResult.get(1).getLastName());
		assertEquals("address2", listResult.get(1).getAddress());
		assertEquals("city2", listResult.get(1).getCity());
		assertEquals(22222, listResult.get(1).getZip());
		assertEquals("phone2", listResult.get(1).getPhone());
		assertEquals("email2", listResult.get(1).getEmail());
		assertEquals("firstName3", listResult.get(2).getFirstName());
		assertEquals("lastName3", listResult.get(2).getLastName());
		assertEquals("address3", listResult.get(2).getAddress());
		assertEquals("city3", listResult.get(2).getCity());
		assertEquals(33333, listResult.get(2).getZip());
		assertEquals("phone3", listResult.get(2).getPhone());
		assertEquals("email3", listResult.get(2).getEmail());
	}

	@Test
	void givenAddressWithTwoPersons_getByAddress_returnsBothPersons()
	{
		// GIVEN
		Person p9 = new Person ("f","l","address2","c",99999,"p","e");
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2, p3, p9));
		dao.set(listGiven);
		// WHEN
		ArrayList<Person> listResult = dao.getByAddress("address2");
		// THEN
		assertEquals(2, listResult.size());
		assertEquals("firstName2", listResult.get(0).getFirstName());
		assertEquals("lastName2", listResult.get(0).getLastName());
		assertEquals("address2", listResult.get(0).getAddress());
		assertEquals("city2", listResult.get(0).getCity());
		assertEquals(22222, listResult.get(0).getZip());
		assertEquals("phone2", listResult.get(0).getPhone());
		assertEquals("email2", listResult.get(0).getEmail());
		assertEquals("f", listResult.get(1).getFirstName());
		assertEquals("l", listResult.get(1).getLastName());
		assertEquals("address2", listResult.get(1).getAddress());
		assertEquals("c", listResult.get(1).getCity());
		assertEquals(99999, listResult.get(1).getZip());
		assertEquals("p", listResult.get(1).getPhone());
		assertEquals("e", listResult.get(1).getEmail());
	}
	
	@Test
	void givenMissingAddress_getByAddress_returnsEmptyList()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2, p3));
		dao.set(listGiven);
		// WHEN
		ArrayList<Person> listResult = dao.getByAddress("non existing address");
		// THEN
		assertEquals(0, listResult.size());
	}
	
	@Test
	void givenExistingP2_getP2_returnsP2()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2, p3));
		dao.set(listGiven);
		// WHEN
		Person p = null;
		try {
			p = dao.get(p2.getFirstName(), p2.getLastName());
		} catch (PersonNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals("firstName2", p.getFirstName());
		assertEquals("lastName2", p.getLastName());
		assertEquals("address2", p.getAddress());
		assertEquals("city2", p.getCity());
		assertEquals(22222, p.getZip());
		assertEquals("phone2", p.getPhone());
		assertEquals("email2", p.getEmail());	
	}

	@Test
	void givenMissingP2_getP2_throwsPersonNotFoundException()
	{
		// GIVEN
		// Empty list
		// WHEN & THEN
		assertThrows(PersonNotFoundException.class, () -> {
			dao.get("firstName2", "lastName2");
		});
	}

@Test
	void saveP1_addsP1()
	{
		// GIVEN
		// Empty list
		// WHEN
		dao.save(p1);
		ArrayList<Person> listResult = dao.getAll();
		// THEN
		assertEquals(1, listResult.size());
		assertEquals("firstName1", listResult.get(0).getFirstName());
		assertEquals("lastName1", listResult.get(0).getLastName());
		assertEquals("address1", listResult.get(0).getAddress());
		assertEquals("city1", listResult.get(0).getCity());
		assertEquals(11111, listResult.get(0).getZip());
		assertEquals("phone1", listResult.get(0).getPhone());
		assertEquals("email1", listResult.get(0).getEmail());
	}

	@Test
	void updateP2_changesP2()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2, p3));
		dao.set(listGiven);
		// WHEN
		Person p2New = new Person ("firstName2","lastName2","address4", "city4", 44444, "phone4", "email4");
		try {
			dao.update(p2New);
		} catch (PersonNotFoundException e) {
			e.printStackTrace();
		}
		Person p = null;
		try {
			p = dao.get("firstName2","lastName2");
		} catch (PersonNotFoundException e) {
			e.printStackTrace();
		}
		// THEN
		assertEquals("firstName2", p.getFirstName());
		assertEquals("lastName2", p.getLastName());
		assertEquals("address4", p.getAddress());
		assertEquals("city4", p.getCity());
		assertEquals(44444, p.getZip());
		assertEquals("phone4", p.getPhone());
		assertEquals("email4", p.getEmail());
	}

	@Test
	void givenMissingP2_updateP2_throwsPersonNotFoundException()
	{
		// GIVEN
		// Empty list
		// WHEN & THEN
		assertThrows(PersonNotFoundException.class, () -> {
			dao.update(p2);
		});
	}

	@Test
	void givenP2_deleteP2_suppressesP2()
	{
		// GIVEN
		ArrayList<Person> listGiven = new ArrayList<Person>(Arrays.asList(p1, p2, p3));
		dao.set(listGiven);
		// WHEN
		dao.delete(p2);
		ArrayList<Person> listResult = dao.getAll();
		// THEN
		assertEquals(2, listResult.size());
		assertEquals("firstName1", listResult.get(0).getFirstName());
		assertEquals("firstName3", listResult.get(1).getFirstName());
	}
}
