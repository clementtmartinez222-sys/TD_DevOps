# Ansible deployment

This directory contains the inventory, playbooks, and roles used to install Docker and deploy the three Docker Hub images.

## Prepare the inventory

Copy `inventories/setup.yml` values to match the managed Debian server:

- `ansible_host`: server IP or DNS name
- `ansible_user`: SSH user
- `ansible_ssh_private_key_file`: private key used for SSH

Test connectivity and inspect facts:

```bash
cd ansible
ansible all -i inventories/setup.yml -m ping
ansible all -i inventories/setup.yml -m setup -a "filter=ansible_distribution*"
```

The inventory is project specific, so it is passed explicitly with `-i`.

## Install and deploy

Install the Docker Python collection once:

```bash
ansible-galaxy collection install -r requirements.yml
ansible-playbook -i inventories/setup.yml playbook.yml --syntax-check
ansible-playbook -i inventories/setup.yml playbook.yml
```

The database password can be supplied without committing it:

```bash
ansible-playbook -i inventories/setup.yml playbook.yml \
  -e "db_password=$POSTGRES_PASSWORD"
```

The playbook installs Docker, creates the application network, and starts PostgreSQL, the API, and the HTTP proxy in that order.

## Continuous deployment

The `deploy` job in GitHub Actions runs after the Docker images are pushed on `main`. Add these repository secrets before using it:

- `ANSIBLE_SSH_PRIVATE_KEY`: private SSH key for the server
- `ANSIBLE_HOST`: server DNS name
- `ANSIBLE_USER`: SSH user
- `ANSIBLE_DB_PASSWORD`: PostgreSQL password

The private key and database password stay in GitHub Actions secrets and are not committed to the repository.
