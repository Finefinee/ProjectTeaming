package Project.Teaming.Member.Response;

import Project.Teaming.Member.Entity.Member;

public record MemberResponse(
        int id,
        String username, //Id
        String name,
        String password,
        int userId // 학번

) {
    public static MemberResponse of (Member member) {
        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getName(),
                member.getPassword(),
                member.getClass_code()
        );
    }

}
