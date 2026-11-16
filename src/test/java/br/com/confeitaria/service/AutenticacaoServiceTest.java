package br.com.confeitaria.service;

import br.com.confeitaria.domain.Atendente;
import br.com.confeitaria.infra.AtendenteRepositoryEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AutenticacaoServiceTest {

    private AutenticacaoService service;

    @BeforeEach
    void setUp() {
        service = new AutenticacaoService(new AtendenteRepositoryEmMemoria());
        service.cadastrarAtendente("admin", "confeitaria123");
    }

    @Test
    void loginComUsuarioESenhaCorretos() {
        assertTrue(service.autenticar("admin", "confeitaria123"));
    }

    @Test
    void usuarioIgnoraMaiusculasEEspacos() {
        assertTrue(service.autenticar("  ADMIN ", "confeitaria123"));
    }

    @Test
    void senhaErradaNaoEntra() {
        assertFalse(service.autenticar("admin", "senhaerrada"));
    }

    @Test
    void usuarioInexistenteNaoEntra() {
        assertFalse(service.autenticar("maria", "confeitaria123"));
    }

    @Test
    void valoresVaziosNaoEntram() {
        assertFalse(service.autenticar(null, null));
    }

    @Test
    void senhaNaoFicaGuardadaEmTextoPuro() {
        Atendente atendente = service.cadastrarAtendente("maria", "segredo1");
        assertNotEquals("segredo1", atendente.getSenhaHash());
        assertEquals(64, atendente.getSenhaHash().length());
    }

    @Test
    void mesmaSenhaGeraSempreOMesmoHash() {
        assertEquals(
            AutenticacaoService.gerarHash("abc123"),
            AutenticacaoService.gerarHash("abc123")
        );
    }

    @Test
    void senhaCurtaEhRecusada() {
        IllegalArgumentException erro = assertThrows(
            IllegalArgumentException.class,
            () -> service.cadastrarAtendente("maria", "12345")
        );
        assertEquals("Senha deve ter pelo menos 6 caracteres", erro.getMessage());
    }

    @Test
    void usuarioVazioEhRecusado() {
        assertThrows(
            IllegalArgumentException.class,
            () -> service.cadastrarAtendente(" ", "segredo1")
        );
    }

    @Test
    void usuarioRepetidoEhRecusado() {
        IllegalArgumentException erro = assertThrows(
            IllegalArgumentException.class,
            () -> service.cadastrarAtendente("Admin", "outrasenha")
        );
        assertEquals("Usuário \"Admin\" já existe", erro.getMessage());
    }
}