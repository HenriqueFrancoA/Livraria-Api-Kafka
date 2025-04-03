import subprocess
import threading

# Lista para armazenar as threads
threads = []

def build_application(app):
    """
    Constrói um microsserviço usando Maven.
    """
    print(f"Building application {app}...")
    try:
        result = subprocess.run(
            ["mvn.cmd", "clean", "package"],  # Use "mvn.cmd" para Windows
            cwd=app,  # Muda para o diretório do serviço
            check=True,  # Lança uma exceção se o comando falhar
            capture_output=True,  # Captura a saída e os erros
            text=True  # Retorna a saída como string
        )
        print(f"Application {app} finished building successfully!")
    except subprocess.CalledProcessError as e:
        print(f"Error building application {app}: {e}")
        print(e.stderr)
        raise
    except Exception as e:
        print(f"Unexpected error building application {app}: {e}")
        raise

def docker_compose_up():
    """
    Executa o docker-compose up --build -d.
    """
    print("Running containers...")
    try:
        result = subprocess.run(
            ["docker-compose", "up", "--build", "--no-cache", "-d"],  # Adiciona --no-cache
            check=True,
            capture_output=True,
            text=True
        )
        print("Pipeline finished!")
        print(result.stdout)
    except subprocess.CalledProcessError as e:
        print(f"Error running docker-compose up: {e}")
        print(e.stderr)
        raise

def build_all_applications():
    """
    Constrói todos os microsserviços em paralelo.
    """
    print("Starting to build applications!")
    services = [
        "address-service",
        "auth-service",
        "book-service",
        "orchestrator-service",
        "order-service",
        "payment-service"
    ]

    for service in services:
        thread = threading.Thread(target=build_application, args=(service,))
        threads.append(thread)
        thread.start()

    # Espera todas as threads terminarem
    for thread in threads:
        thread.join()

def remove_remaining_containers():
    """
    Remove todos os contêineres existentes.
    """
    print("Removing all containers...")
    try:
        # Para e remove os contêineres do docker-compose
        subprocess.run(
            ["docker-compose", "down"],
            check=True,
            capture_output=True,
            text=True
        )

        # Lista os contêineres restantes
        result = subprocess.run(
            ["docker", "ps", "-aq"],
            check=True,
            capture_output=True,
            text=True
        )
        containers = result.stdout.strip().split("\n")
        containers = [c for c in containers if c]  # Remove entradas vazias

        if containers:
            print(f"There are still {len(containers)} containers running: {containers}")
            for container in containers:
                print(f"Stopping container {container}...")
                subprocess.run(
                    ["docker", "container", "stop", container],
                    check=True,
                    capture_output=True,
                    text=True
                )
            # Remove os contêineres parados
            subprocess.run(
                ["docker", "container", "prune", "-f"],
                check=True,
                capture_output=True,
                text=True
            )
        else:
            print("No remaining containers to remove.")
    except subprocess.CalledProcessError as e:
        print(f"Error removing containers: {e}")
        print(e.stderr)
        raise

if __name__ == "__main__":
    print("Pipeline started!")
    try:
        # Constrói todos os microsserviços
        build_all_applications()

        # Remove os contêineres existentes
        remove_remaining_containers()

        # Inicia o docker-compose
        docker_compose_up()

    except Exception as e:
        print(f"Pipeline failed: {e}")
        exit(1)