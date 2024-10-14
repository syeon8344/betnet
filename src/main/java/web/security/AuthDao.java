package web.security;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthDao {
    AuthDto findByUsername(String username);
}
