package cn.leegq.course6;

public class SqlSessionFactory {

  public static SqlSession openSession(){
      return new DefaultSqlSession(Configuration.getInstance(),TransactionFactory.openTransaction());
  }
}