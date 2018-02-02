
没必要的接口没有写。

@Service
public class StudentServiceImpl implements StudentService {
	
	private StudentDao studentDao;
	
	@Autowired
	@Qualifier("studentDaoId")
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	public void addStudent() {
		studentDao.save();
	}

}


@Repository("studentDaoId")
public class StudentDaoImpl implements StudentDao   ---save()方法


@Controller("studentActionId")
public class StudentAction {
	
	@Autowired 
	private StudentService studentService;

	public void execute() {
		studentService.addStudent();
	}

}

(StudentAction)applicationContext.getBean("studentActionId").exrcute();

根据studentActionId生成实例，里面又根据类型匹配StudentService,就转到了实现类
StudentServiceImpl标记了service，可能是需要的就加id，不需要的可以直接通过类型匹配。
里面又根据名称来实例化studentDaoId



