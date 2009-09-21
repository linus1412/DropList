import org.springframework.security.providers.*

class TestController {

    def ldapAuthProvider
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("martinsmith@blueskyts.co.uk", "L1verpool")
    def index = { render text:ldapAuthProvider.authenticate(token) }
}
