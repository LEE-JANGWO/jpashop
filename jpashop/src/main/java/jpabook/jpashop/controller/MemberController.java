package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")    // 위에 get방식도 같은 주소이지만 get은 form화면을 열어보고  post는 form을 실제 등록하는 것
    public String create(@Valid MemberForm memberForm, BindingResult result) {   // createMemberForm.html에서 memberForm이 파라미터로 넘어온다다
                                // ENTITY인 MEMBER로 데이터를 받아 저장하지 말고, 화면에 맞는 FORM을 만들어서 데이터를 받아 엔티티에 저장하는게 더 좋다
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());  // member entity를 직접적으로 사용하기보다는 memberForm으로 값을 받아 전달 ( entity는 최대한 순수하게 유지하기 위해 )
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
   }

   @GetMapping("/members")
    public String list(Model model) {
       List<Member> members = memberService.findMembers();  // memberService.findMembers(); 까지 입력한 후 ctrl + alt + v 누르면  저장할 객체 자동 생성 됨  memberForm으로 받았지만 member entity를 출력 ( 실무에서는 직접적으로 entity를 출력하기 보다는 dto를 만들어서 필요한 값만 받아서 출력하도록 한다)
       model.addAttribute("members", members);   // API를 만들 때는 절대로 ENTITY를 넘기면 안된다.
//       model.addAttribute("members", memberService.findMembers());   // 위에랑 같은거
       return "members/memberList";
   }
}
