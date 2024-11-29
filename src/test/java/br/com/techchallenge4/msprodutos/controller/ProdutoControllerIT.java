package br.com.techchallenge4.msprodutos.controller;

import br.com.techchallenge4.msprodutos.utils.ProdutoHelper;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProdutoControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void devePermitirSalvarUmProduto() {
        var produto = ProdutoHelper.criarProduto();

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produto)
                .when()
                .post("/api/v1/produtos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("nome", equalTo(produto.nome()));
    }

    @Test
    void devePermitirListarProdutos() {

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/produtos")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("number", equalTo(0))
                .body("size", equalTo(10))
                .body("totalElements", equalTo(1))
                ;
    }

}
