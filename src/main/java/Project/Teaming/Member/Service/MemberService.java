package Project.Teaming.Member.Service;

import Project.Teaming.Member.Entity.Member;
import Project.Teaming.Member.Interface.MemberRepository;
import Project.Teaming.Member.Request.CreateMemberRequest;
import Project.Teaming.Member.Request.DeleteMemberRequest;
import Project.Teaming.Member.Request.UpdateMember;
import Project.Teaming.Member.Response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepository MemberRepository;

    public void createMember(CreateMemberRequest request) { //회원가입
        Member member = Member.builder()  //왜 난리야 ㅡㅡ
                .username(request.username())
                .name(request.name())
                .password(request.password())
                .userId(request.userId())
                .build();

        MemberRepository.save(member);
    }
    public String delete(DeleteMemberRequest request) { //회원 탈퇴
        Member member = MemberRepository.findById(request.id()).orElse(null);

        if (member == null) {
            throw new IllegalArgumentException(request.id() + "는 없는 사람입니다");
        }
        MemberRepository.delete(member);
        return "성공적으로 탈퇴되었습니다.";
    }
    public MemberResponse findMemberById(int id) {
        Member e = MemberRepository.findById(id).orElse(null);
        if (e == null) {
            throw new IllegalArgumentException(id + "는 없는 사람입니다");
        }
        return MemberResponse.of(e);
    }
    public void deleteMemberById(int id) {
        Member e = MemberRepository.findById(id).orElse(null);
        if (e == null) {
            throw new IllegalArgumentException(id + "없는 사람입니다");
        }
        MemberRepository.delete(e);
    }
    public MemberResponse updateMember(UpdateMember request) { // 프로필 수정
        Member e = MemberRepository.findById(request.id()).orElse(null);
        if (e == null) {
            throw new IllegalArgumentException(request.id()+"는 없는 사람입니다.");
        }
        e.setId(request.id());

        MemberRepository.save(e);

        return MemberResponse.of(e);
    }

    public List<MemberResponse> findAll() {
        return MemberRepository.findAll().stream().map(MemberResponse::of).collect(Collectors.toList());
    }
}
