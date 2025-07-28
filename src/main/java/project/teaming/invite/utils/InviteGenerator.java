package project.teaming.invite.utils;

import org.springframework.stereotype.Component;
import project.teaming.invite.entity.Invite;
import project.teaming.member.entity.Member;
import project.teaming.project.entity.Project;

@Component
public class InviteGenerator {
    public Invite create(Member manager, Member invitee, Project project) {
        Invite invite = new Invite();
        invite.setProjectManager(manager);
        invite.setProjectMember(invitee);
        invite.setProject(project);
        invite.setAccepted(false);
        return invite;
    }
}
