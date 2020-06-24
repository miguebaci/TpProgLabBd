package utn.edu.tpfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class TpfinalApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		SpringApplication.run(TpfinalApplication.class, args);
			/*
			MessageDigest m = MessageDigest.getInstance("MD5");
			byte[] data = "antenna".getBytes();
			m.update(data, 0, data.length);
			BigInteger i = new BigInteger(1, m.digest());
			System.out.println(String.format("%1$032X", i));
			*/
	}

}
