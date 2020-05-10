
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.laboratory.dao.MemberMapper;
import com.laboratory.po.Classes;
import com.laboratory.po.Member;
import com.laboratory.po.News;
import com.laboratory.dao.ClassesMapper;
import com.laboratory.service.NewsService;
import com.laboratory.service.MemberService;
import com.laboratory.service.ClassesService;
import com.laboratory.util.NameSpaceUtil;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class ServiceTest {
	
	@Autowired
	MemberService ms;
	
	@Autowired
	NewsService as;
	
	@Autowired
	ClassesService rs;
	
	@Autowired
	MemberMapper mm;
	
	
	
	@Test
	public void artilceTest(){
		List<News> articles = as.findByPage(1, 5);
		for(News a:articles){
			System.out.println(a.getTitle());
			System.out.println(a.getDate());
		}
		
	}
	
	@Test
	public void researchPageTest(){
		List<Classes> researchs = rs.findByPage(1, 4);
		for(Classes r:researchs){
			System.out.println(r.getId());
			System.out.println(r.getSumary());
			
		}
	}
	
	@Test
	public void articlePageTest(){
		List<News> articles =  as.findByPage(1, 5);
		System.out.println(articles);
	}
	
//	@Test
//	public void yearTest(){
//		List<String> years = as.findArticleYear();
//		for(String s:years){
//			System.out.println(s);
//		}
//	}
	
}
	
	

