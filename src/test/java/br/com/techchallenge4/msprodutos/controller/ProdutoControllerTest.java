package br.com.techchallenge4.msprodutos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.callibrity.logging.test.LogTracker;
import com.callibrity.logging.test.LogTrackerStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.techchallenge4.msprodutos.handler.GlobalExceptionHandler;
import br.com.techchallenge4.msprodutos.dto.ProdutoDTO;
import br.com.techchallenge4.msprodutos.service.ProdutoService;
import br.com.techchallenge4.msprodutos.utils.ProdutoHelper;


@SpringBootTest
class ProdutoControllerTest {

    private MockMvc mockMvc;

    @RegisterExtension
    LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
            .recordForType(ProdutoController.class);

    @Mock
    private ProdutoService produtoService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        ProdutoController produtoController = new ProdutoController(produtoService);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirBuscarUmProduto() throws Exception {
        var id = 1L;
        var produto = ProdutoHelper.criarProduto();

        when(produtoService.getProduto(any(Long.class))).thenReturn(produto);

        mockMvc.perform(get("/api/v1/produtos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(produto.nome().toString()));
        verify(produtoService, times(1)).getProduto(any(Long.class));
    }

    @Test
    void devePermirirAlterarUmProduto() throws Exception {
        var id = 1L;
        var produto = ProdutoHelper.criarProduto();

        when(produtoService.updateProduto(any(Long.class), any(ProdutoDTO.class)))
                .thenAnswer(i -> i.getArgument(1));

        mockMvc.perform(put("/api/v1/produtos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(produto.nome()));
        verify(produtoService, times(1))
                .updateProduto(any(Long.class), any(ProdutoDTO.class));
    }

    @Test
    void devePermitirApagarUmProduto() throws Exception {
        var id = 1L;

        when(produtoService.deleteProduto(any(Long.class)))
                .thenReturn(true);

        mockMvc.perform(delete("/api/v1/produtos/{id}", id))
                .andExpect(status().isNoContent());
        verify(produtoService, times(1))
                .deleteProduto(any(Long.class));
    }

    public static String asJsonString(final Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
