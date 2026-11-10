// Pipeline Jenkins equivalente ao .github/workflows/cicd.yml.
// Requer: agente Docker, credencial 'harbor-creds' (usuário/senha do robô) e
// credencial 'github-token' (escrita no repo) para atualizar o manifest dev.

pipeline {
    agent {
        docker {
            image 'maven:3.9-eclipse-temurin-21'
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    environment {
        HARBOR_REGISTRY = credentials('harbor-registry-host') // ou use params/vars
        HARBOR_PROJECT  = 'dependabot'
        IMAGE_NAME      = 'dependabot-test-mini'
        IMAGE_TAG       = "${env.GIT_COMMIT?.take(12) ?: 'local'}"
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Test') {
            steps {
                sh 'mvn --batch-mode --errors verify'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build image') {
            when { branch pattern: 'develop|main', comparator: 'REGEXP' }
            steps {
                sh 'docker build -t ${HARBOR_REGISTRY}/${HARBOR_PROJECT}/${IMAGE_NAME}:${IMAGE_TAG} .'
            }
        }

        stage('Push to Harbor') {
            when { branch pattern: 'develop|main', comparator: 'REGEXP' }
            steps {
                withCredentials([usernamePassword(credentialsId: 'harbor-creds',
                                                  usernameVariable: 'HARBOR_USER',
                                                  passwordVariable: 'HARBOR_PASS')]) {
                    sh '''
                        echo "$HARBOR_PASS" | docker login "$HARBOR_REGISTRY" -u "$HARBOR_USER" --password-stdin
                        docker push "$HARBOR_REGISTRY/$HARBOR_PROJECT/$IMAGE_NAME:$IMAGE_TAG"
                    '''
                }
            }
        }

        stage('Update dev manifest') {
            when { branch 'develop' }
            steps {
                withCredentials([string(credentialsId: 'github-token', variable: 'GH_TOKEN')]) {
                    sh '''
                        OVERLAY=deploy/k8s/overlays/dev/kustomization.yaml
                        NAME="$HARBOR_REGISTRY/$HARBOR_PROJECT/$IMAGE_NAME"
                        sed -i -E "s|(newName: ).*|\\1${NAME}|; s|(newTag: ).*|\\1\\"${IMAGE_TAG}\\"|" "$OVERLAY"
                        git config user.name "jenkins"
                        git config user.email "jenkins@localhost"
                        git add "$OVERLAY"
                        git diff --cached --quiet || git commit -m "chore(dev): deploy ${IMAGE_TAG} [skip ci]"
                        git push "https://x-access-token:${GH_TOKEN}@github.com/henriquebatistapaz/dependabot-test-mini.git" HEAD:develop
                    '''
                }
            }
        }
    }
}
