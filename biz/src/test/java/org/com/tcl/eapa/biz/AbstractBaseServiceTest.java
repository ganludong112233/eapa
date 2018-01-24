package org.com.tcl.eapa.biz;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:applicationContext-biz-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractBaseServiceTest {

}
