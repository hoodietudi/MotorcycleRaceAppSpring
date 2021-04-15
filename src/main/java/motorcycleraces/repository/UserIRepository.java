package motorcycleraces.repository;

import motorcycleraces.domain.User;
//import org.graalvm.compiler.lir.LIRInstruction;

public interface UserIRepository extends IRepository<Long, User>{
    User searchForUser(String username, String password);
}
