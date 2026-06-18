package br.edu.ifsudestemg.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    private static final Map<String, ResourceDocumentation> RESOURCES = Map.ofEntries(
            Map.entry("BombaController", new ResourceDocumentation("bombas", "bomba", "Gerencia as bombas cadastradas nos postos.")),
            Map.entry("ClienteController", new ResourceDocumentation("clientes", "cliente", "Gerencia os clientes e seus dados de fidelidade.")),
            Map.entry("CombustivelController", new ResourceDocumentation("combustiveis", "combustível", "Gerencia combustíveis vendidos nos postos.")),
            Map.entry("CompraController", new ResourceDocumentation("compras", "compra", "Gerencia compras e notas fiscais de entrada.")),
            Map.entry("FornecedorController", new ResourceDocumentation("fornecedores", "fornecedor", "Gerencia fornecedores vinculados aos postos.")),
            Map.entry("FuncionarioController", new ResourceDocumentation("funcionarios", "funcionário", "Gerencia funcionários cadastrados no sistema.")),
            Map.entry("PostoController", new ResourceDocumentation("postos", "posto", "Gerencia postos de combustíveis.")),
            Map.entry("ProdutoController", new ResourceDocumentation("produtos", "produto", "Gerencia produtos vendidos nos postos.")),
            Map.entry("PromocaoController", new ResourceDocumentation("promocoes", "promoção", "Gerencia promoções e descontos.")),
            Map.entry("RegistroPrecoProdutoController", new ResourceDocumentation("registros-preco-produto", "registro de preço de produto", "Gerencia histórico de preços de produtos.")),
            Map.entry("RegistroPrecoServicoController", new ResourceDocumentation("registros-preco-servico", "registro de preço de serviço", "Gerencia histórico de preços de serviços.")),
            Map.entry("ServicoController", new ResourceDocumentation("servicos", "serviço", "Gerencia serviços prestados nos postos.")),
            Map.entry("TurnoController", new ResourceDocumentation("turnos", "turno", "Gerencia turnos de trabalho.")),
            Map.entry("UsarioController", new ResourceDocumentation("usuarios", "usuario", "Gerencia usuarios e autenticacao JWT.")),
            Map.entry("VendaController", new ResourceDocumentation("vendas", "venda", "Gerencia vendas realizadas nos postos."))
    );

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("POSTOBACK API")
                        .description("API REST do sistema de gestão de postos de combustíveis. Os endpoints estão versionados em /api/v1 e agrupados por recurso.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Allan Mahisson, Pedro Faria, Marco Gabriel")
                                .url("https://github.com/omahisson/postoBack")
                                .email("mahisson@icloud.com")
                        )
                )
                .servers(List.of(new Server()
                        .url("http://localhost:8081")
                        .description("Ambiente local de desenvolvimento")))
                .tags(RESOURCES.values().stream()
                        .map(resource -> new Tag()
                                .name(resource.tag())
                                .description(resource.description()))
                        .toList())
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                        .addResponses("BadRequest", new ApiResponse()
                                .description("Requisição inválida ou regra de negócio não atendida")
                                .content(errorContent()))
                        .addResponses("NotFound", new ApiResponse()
                                .description("Recurso não encontrado")));
    }

    @Bean
    public GroupedOpenApi publicApi(OperationCustomizer operationCustomizer) {
        return GroupedOpenApi.builder()
                .group("POSTOBACK - API v1")
                .packagesToScan("br.edu.ifsudestemg.demo.api.controller")
                .addOperationCustomizer(operationCustomizer)
                .build();
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            ResourceDocumentation resource = resourceFrom(handlerMethod).orElse(null);
            if (resource == null) {
                return operation;
            }

            operation.setTags(List.of(resource.tag()));

            String methodName = handlerMethod.getMethod().getName();
            boolean isUsuarioController = handlerMethod.getBeanType().getSimpleName().equals("UsarioController");
            boolean isPublicUsuarioEndpoint = isUsuarioController && (methodName.equals("post") || methodName.equals("autenticar"));

            if (!isPublicUsuarioEndpoint) {
                operation.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
            }

            if (methodName.equals("autenticar")) {
                operation.setSummary("Autenticar usuario");
                operation.setDescription("Recebe login e senha e retorna um token JWT. Use o token no botao Authorize do Swagger.");
                describeResponse(operation.getResponses(), "200", "Token gerado com sucesso");
                operation.getResponses().addApiResponse("401", new ApiResponse().description("Login ou senha invalidos"));
            } else if (methodName.equals("get") && isDetailGet(handlerMethod)) {
                operation.setSummary("Obter detalhes de " + resource.singular());
                operation.setDescription("Busca um único registro de " + resource.singular() + " pelo identificador informado na rota.");
                describeResponse(operation.getResponses(), "200", "Registro encontrado");
                operation.getResponses().addApiResponse("404", new ApiResponse().$ref("#/components/responses/NotFound"));
            } else if (methodName.equals("get")) {
                operation.setSummary("Listar " + resource.tag());
                operation.setDescription("Retorna todos os registros de " + resource.tag() + " cadastrados.");
                describeResponse(operation.getResponses(), "200", "Lista retornada com sucesso");
            } else if (methodName.equals("criar") || methodName.equals("post")) {
                operation.setSummary("Salvar novo " + resource.singular());
                operation.setDescription("Cria um novo registro de " + resource.singular() + " a partir dos dados enviados no corpo da requisicao. Envie um unico objeto JSON entre chaves { }, nao uma lista entre colchetes [ ]. O campo id pode ser omitido, pois o banco gera esse valor automaticamente.");
                describeResponse(operation.getResponses(), "201", "Registro criado com sucesso");
                operation.getResponses().addApiResponse("400", new ApiResponse().$ref("#/components/responses/BadRequest"));
            } else if (methodName.equals("atualizar") || methodName.equals("put")) {
                operation.setSummary("Atualizar " + resource.singular());
                operation.setDescription("Atualiza um registro existente de " + resource.singular() + " pelo identificador informado na rota.");
                describeResponse(operation.getResponses(), "200", "Registro atualizado com sucesso");
                operation.getResponses().addApiResponse("400", new ApiResponse().$ref("#/components/responses/BadRequest"));
                operation.getResponses().addApiResponse("404", new ApiResponse().$ref("#/components/responses/NotFound"));
            } else if (methodName.equals("excluir")) {
                operation.setSummary("Excluir " + resource.singular());
                operation.setDescription("Remove um registro de " + resource.singular() + " pelo identificador informado na rota.");
                operation.getResponses().remove("200");
                operation.getResponses().addApiResponse("204", new ApiResponse().description("Registro excluído com sucesso"));
                operation.getResponses().addApiResponse("400", new ApiResponse().$ref("#/components/responses/BadRequest"));
                operation.getResponses().addApiResponse("404", new ApiResponse().$ref("#/components/responses/NotFound"));
            }

            return operation;
        };
    }

    private Optional<ResourceDocumentation> resourceFrom(HandlerMethod handlerMethod) {
        return Optional.ofNullable(RESOURCES.get(handlerMethod.getBeanType().getSimpleName()));
    }

    private boolean isDetailGet(HandlerMethod handlerMethod) {
        GetMapping mapping = handlerMethod.getMethodAnnotation(GetMapping.class);
        if (mapping == null) {
            return false;
        }
        return hasIdPath(mapping.value()) || hasIdPath(mapping.path());
    }

    private boolean hasIdPath(String[] paths) {
        for (String path : paths) {
            if (path.contains("{id}")) {
                return true;
            }
        }
        return false;
    }

    private void describeResponse(Map<String, ApiResponse> responses, String status, String description) {
        ApiResponse response = responses.get(status);
        if (response == null) {
            responses.put(status, new ApiResponse().description(description));
            return;
        }
        response.setDescription(description);
    }

    private Content errorContent() {
        return new Content().addMediaType("application/json", new MediaType()
                .example(Map.of(
                        "status", "400 BAD_REQUEST",
                        "mensagem", "Mensagem de erro da regra de negócio",
                        "timestamp", "2026-06-08T19:30:00"
                )));
    }

    private record ResourceDocumentation(String tag, String singular, String description) {
    }
}
