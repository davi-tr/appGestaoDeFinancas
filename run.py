import psycopg2
import subprocess

def delete_all_tables():
    try:
        # Conectar ao banco de dados PostgreSQL
        conn = psycopg2.connect(
            dbname="mobileapp",
            user="postgres",
            password="Banco123",
            host="localhost",
            port="5432"
        )

        # Criar um cursor
        cur = conn.cursor()

        # Solicitar confirmação do usuário
        confirm = input("Tem certeza de que deseja excluir todas as tabelas? (s/n): ").lower()
        if confirm != 's':
            print("Operação cancelada.")
            return

        # Consulta SQL para listar todas as tabelas no schema public
        cur.execute("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE';")
        tables = cur.fetchall()

        # Deletar todas as tabelas encontradas
        for table in tables:
            cur.execute("DROP TABLE IF EXISTS {} CASCADE;".format(table[0]))

        # Confirmar as alterações
        conn.commit()

        print("Todas as tabelas foram deletadas com sucesso.")

    except Exception as e:
        print("Ocorreu um erro:", e)

    finally:
        # Fechar o cursor e a conexão
        cur.close()
        conn.close()

# Executar a função para deletar todas as tabelas
delete_all_tables()

# Depois de deletar as tabelas, executar o script PowerShell
subprocess.run(["git", "pull"])
subprocess.run(["java", "-jar", "target\gestao-0.0.5-BETA.jar"])