# sweet-manager
# 🍰 Sweet-Manager

**Sweet-Manager** é um sistema **web fullstack** para **gestão completa de uma confeitaria**, permitindo o controle de **pedidos, estoque, insumos e produção**, além de um **painel administrativo** com métricas e relatórios.

---

## 🚀 Funcionalidades principais

- 🧁 Cadastro e gerenciamento de produtos e insumos  
- 🧾 Registro de pedidos com atualização automática de estoque  
- 📉 Controle de produção e consumo de ingredientes  
- 📊 Dashboard com métricas de vendas e relatórios de consumo  
- 👩‍💻 Autenticação JWT e controle de acesso por perfil  
- 🔎 API REST documentada e padronizada (Swagger)  

---

## 🧠 Tecnologias utilizadas

### Backend
- **Java 17** + **Spring Boot 3**
- **PostgreSQL** (persistência)
- **JPA / Hibernate** (ORM)
- **Spring Security + JWT** (autenticação)
- **Lombok** e **Validation API**
- **Swagger/OpenAPI** (documentação)

### Frontend
- **ReactJS** + **Vite**
- **TailwindCSS** (estilização)
- **Express** (controle de rotas e comunicação com a API)
- **Recharts** (visualização de dados)
- **Axios** substituído por **fetch** nativo para consistência

---

## 🧩 Arquitetura

O projeto segue princípios de **Clean Architecture**, separando camadas de:
- `controller` → comunicação via endpoints REST  
- `service` → lógica de negócio e regras  
- `repository` → persistência e integração com o banco  
- `dto` → transferência de dados entre camadas  

No frontend, a estrutura é modularizada por páginas, componentes reutilizáveis e rotas independentes no **Express**, simulando um ambiente empresarial real.

---

## ⚙️ Como rodar o projeto

### 🔹 Pré-requisitos
- Node.js 18+
- Java 17+
- PostgreSQL 14+
- Maven

### 🔹 Backend

```bash
cd backend
mvn spring-boot:run
