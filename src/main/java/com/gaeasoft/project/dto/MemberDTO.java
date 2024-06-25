package com.gaeasoft.project.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.gaeasoft.project.util.EncodePassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	
	public Long memberSeq;
	
	@NotBlank(message = "이름을 입력해 주세요.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$", message = "이름에는 특수 문자를 사용할 수 없습니다.")
    @Size(max = 50, message = "이름은 50자 이하이어야 합니다.")
	public String name;
    
	@NotBlank(message = "아이디를 입력해 주세요.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$", message = "아이디에는 특수 문자를 사용할 수 없습니다.")
    @Size(max = 50, message = "아이디는 50자 이하이어야 합니다.")
	public String memberId;
    
	@NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(max = 64, message = "비밀번호는 64자 이하이어야 합니다.")
	public String password;
	
	@NotBlank(message = "이메일을 입력해 주세요.")
	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "유효한 이메일 형식이 아닙니다.")
    @Size(max = 100, message = "이메일은 100자 이하이어야 합니다.")
	public String email;
	
	public Timestamp joinTime;
	
	@NotBlank(message = "주소를 입력해 주세요.")
    @Size(max = 200, message = "주소는 200자 이하이어야 합니다.")
	public String address;
	
	@NotBlank(message = "상세주소를 입력해 주세요.")
	public String detailAddress;
	public boolean isDeleted;
	
	@Override
	public String toString() {
		String encodePassword = EncodePassword.encrypt(password);
		return "MemberDTO [name=" + name + ", id=" + memberId + ", password=" + encodePassword + ", email=" + email + "]";
	}
	
}
