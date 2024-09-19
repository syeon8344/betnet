package web.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.dto.MemberDto;
import web.model.dto.TeamsDto;

import java.util.List;

@Mapper
public interface MemberDao {
    //09.09 회원가입
    boolean signUp(MemberDto memberDto);

    //09.10 로그인
    MemberDto login(MemberDto memberDto);

    //09.11 id중복검사
    boolean idChecking(String userName);

    //09.12 전화번호 중복검사
    boolean phoneCheck(String contact);

    //09.12 팀정보 가져오기
    List<TeamsDto> teams();

    //09.12 이메일중복검사
    boolean emailCheck(String email);

    MemberDto logCheck(int memberid);
}
