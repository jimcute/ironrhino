package initdata;

import org.ironrhino.core.model.Persistable;
import org.ironrhino.core.service.EntityManager;
import org.ironrhino.core.util.CodecUtils;
import org.ironrhino.security.model.User;
import org.ironrhino.security.model.UserRole;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("rawtypes")
public class InitUser {

	static EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public static void main(String... strings) throws Exception {
		System.setProperty("app.name", "ironrhino");
		System.setProperty("ironrhino.home", System.getProperty("user.home")
				+ "/" + System.getProperty("app.name"));
		System.setProperty("ironrhino.context", System.getProperty("user.home")
				+ "/" + System.getProperty("app.name"));
		System.out.println("initialize:" + User.class);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "initdata/applicationContext-initdata.xml",
						"resources/spring/applicationContext-ds.xml",
						"resources/spring/applicationContext-hibernate.xml" });
		EntityManager<Persistable> entityManager = (EntityManager<Persistable>) ctx
				.getBean("entityManager");
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(CodecUtils.digest("password"));
		admin.setEnabled(true);
		admin.getRoles().add(UserRole.ROLE_ADMINISTRATOR);
		entityManager.save(admin);
		ctx.close();
	}

}
