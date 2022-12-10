package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buff.type.Enhance;
import entity.base.Buff;

public class BuffTest {

	@BeforeEach
	void setUp() throws Exception {
		Buff b = new Enhance(0, 0);
	}

	@Test
	void test() {
		Buff b = new Enhance(0, 0);
		//Enhance enhance = new Buff(2,3);
	}

}
