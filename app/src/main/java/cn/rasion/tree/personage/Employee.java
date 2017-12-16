package cn.rasion.tree.personage;

import java.io.Serializable;

public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 员工私有属性
	 */
	private String id;// 员工编号
	private String name;// 员工姓名
	private String level;// 员工级别
	private String number;// 员工电话
	private String hiredate;// 员工入职日期

	private int salary;// 员工工资

	private String leaderId;// 上级编号
	private String leftUnderId;// 下级左支编号
	private String rightUnderId;// 下级右支编号

	private String accountNumber;// 会员帐号
	private String accountPassword;// 账户密码

	/**
	 * 构造方法区
	 */
	public Employee() {

        this.id = "None";
        this.name = "None";
        this.level = "None";
        this.number = "None";
        this.hiredate = "None";
        this.salary = 0;
        this.leaderId = "None";
        this.leftUnderId = "None";
        this.rightUnderId = "None";
        this.accountNumber = "None";
        this.accountPassword = "None";
	}

	/**
	 * 有参构造
	 * 
	 * @param id
	 *            员工编号
	 * @param name
	 *            员工姓名
	 * @param level
	 *            员工级别
	 * @param number
	 *            员工电话
	 * @param hiredate
	 *            员工入职日期
	 * @param salary
	 *            员工工资
	 * @param leaderId
	 *            上级编号
	 * @param leftUnderId
	 *            下级左支编号
	 * @param rightUnderId
	 *            下级右支编号
	 * @param accountNumber
	 *            会员帐号
	 * @param accountPassword
	 *            账户密码
	 */
	public Employee(String id, String name, String level, String number, String hiredate, int salary, String leaderId,
			String leftUnderId, String rightUnderId, String accountNumber, String accountPassword) {
		super();

		this.id = id;
		this.name = name;
		this.level = level;
		this.number = number;
		this.hiredate = hiredate;
		this.salary = salary;
		this.leaderId = leaderId;
		this.leftUnderId = leftUnderId;
		this.rightUnderId = rightUnderId;
		this.accountNumber = accountNumber;
		this.accountPassword = accountPassword;

	}



	public Employee(String id, String name, String level, String number, String hiredate, String
            salaryStr, String leaderId, String leftUnderId, String rightUnderId, String accountNumber,
                    String accountPassword) {
        if ("".equals(id)) {// 若输入为空则转换默认值
            id = "None";
        }

        if ("".equals(name)) {
            name = "None";
        }

        if ("".equals(level)) {
            level = "None";
        }

        if ("".equals(number)) {
            number = "None";
        }

        if ("".equals(hiredate)) {
            hiredate = "None";
        }

        int salary;
        if ("".equals(salaryStr)) {
            salary = 0;
        } else {
            salary = Integer.parseInt(salaryStr);
        }

        if ("".equals(leaderId)) {
            leaderId = "None";
        }

        if ("".equals(leftUnderId)) {
            leftUnderId = "None";
        }

        if ("".equals(rightUnderId)) {
            rightUnderId = "None";
        }

        if ("".equals(accountNumber)) {
            accountNumber = "None";
        }

        if ("".equals(accountPassword)) {
            accountPassword = "None";
        }

        this.id = id;
        this.name = name;
        this.level = level;
        this.number = number;
        this.hiredate = hiredate;
        this.salary = salary;
        this.leaderId = leaderId;
        this.leftUnderId = leftUnderId;
        this.rightUnderId = rightUnderId;
        this.accountNumber = accountNumber;
        this.accountPassword = accountPassword;
	}

	/**
	 * 封装方法
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeftUnderId() {
		return leftUnderId;
	}

	public void setLeftUnderId(String leftUnderId) {
		this.leftUnderId = leftUnderId;
	}

	public String getRightUnderId() {
		return rightUnderId;
	}

	public void setRightUnderId(String rightUnderId) {
		this.rightUnderId = rightUnderId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
}
