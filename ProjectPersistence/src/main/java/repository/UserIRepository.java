package repository;

import domain.User;
//import org.graalvm.compiler.lir.LIRInstruction;

public interface UserIRepository extends IRepository<Long, User>{
    User searchForUser(String username, String password);
}
