package com.mycompany.webapp.security;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {
	//비밀키(노출이 되면 안된다.)
	private static final String secretKey = "12345";
	
	//JWT 생성
	//개인정보 JWT에 저장시키면 안됨 비밀번호 같은 
	public static String createToken(String mid, String authority) {
		log.info("실행");
		String result = null;
		try {
		String token = Jwts.builder()
				//헤더 설정
				.setHeaderParam("alg", "HS256")
				.setHeaderParam("typ", "jwt")
				//토큰의 유효기간
				.setExpiration(new Date(new Date().getTime()+1000*3600*24))
				//페이로드 설정
				.claim("mid",mid)
				.claim("authority",authority)
				//서명 설정
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))
				// 토큰 생성
				.compact();
			result = token;
		}catch(Exception e) {
			log.info(e.getMessage());
		}
		return result;
	}
	
	//JWT 유효성 검사
	public static Claims validateToken(String token){
		log.info("실행");
		Claims result = null;
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(secretKey.getBytes("UTF-8"))
					.parseClaimsJws(token)
					.getBody();
			log.info("1");

			result = claims;

		}catch (Exception e) {
		}
		return result;
	}
	
	//JWT에서 정보 얻기
	public static String getMid(Claims claims) {
		log.info("실행");
		return claims.get("mid",String.class);
	}
	
	
	//JWT에서 정보 얻기
	public static String getAuthority(Claims claims) {
		log.info("실행");
		return claims.get("authority",String.class);
	}
	
	//확인
	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		// 토큰 생성
		String mid = "user";
		String authority = "ROLE_USER";
		String jwt = createToken(mid, authority);
		
		System.out.println(jwt);
		
		//Thread.sleep(2000);
		
		//토큰 유효성 검사 
		Claims claims = validateToken(jwt);
		if(claims != null) {
			log.info("유효한 토큰");
			log.info("mid: "+getMid(claims));
			log.info("authority: "+getAuthority(claims));
		}else {
			log.info("유효하지 않은 토큰");
		}
	}
}
