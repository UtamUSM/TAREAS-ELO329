#include "mainwindow.h"
#include "ui_mainwindow.h"

#include "broker.h"
#include "videopublisher.h"
#include "videofollower.h"
#include "gpscarpublisher.h"
#include "gpsfollower.h"

#include <QPushButton>
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QInputDialog>
#include <QMessageBox>
#include <QLabel>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent),
    ui(new Ui::MainWindow),
    broker(new Broker)
{
    ui->setupUi(this);

    // Crear contenedor principal sin usar mainwindow.ui
    centralWidgetContainer = new QWidget(this);
    this->setCentralWidget(centralWidgetContainer);

    // Layouts
    QHBoxLayout* mainLayout = new QHBoxLayout(centralWidgetContainer);
    QVBoxLayout* controlLayout = new QVBoxLayout();
    publisherLayout = new QVBoxLayout();
    subscriberLayout = new QVBoxLayout();

    // Botones
    QPushButton* addVideoPubBtn = new QPushButton("Agregar Video Publisher");
    QPushButton* addVideoSubBtn = new QPushButton("Agregar Video Follower");
    QPushButton* addGpsPubBtn   = new QPushButton("Agregar GPS Publisher");
    QPushButton* addGpsSubBtn   = new QPushButton("Agregar GPS Follower");

    controlLayout->addWidget(addVideoPubBtn);
    controlLayout->addWidget(addVideoSubBtn);
    controlLayout->addWidget(addGpsPubBtn);
    controlLayout->addWidget(addGpsSubBtn);
    controlLayout->addStretch();

    // Armar layout principal
    mainLayout->addLayout(controlLayout, 1);
    mainLayout->addLayout(publisherLayout, 2);
    mainLayout->addLayout(subscriberLayout, 2);
    centralWidgetContainer->setLayout(mainLayout);

    // Conexiones
    connect(addVideoPubBtn, &QPushButton::clicked, this, &MainWindow::createVideoPublisher);
    connect(addVideoSubBtn, &QPushButton::clicked, this, &MainWindow::createVideoFollower);
    connect(addGpsPubBtn,   &QPushButton::clicked, this, &MainWindow::createGpsPublisher);
    connect(addGpsSubBtn,   &QPushButton::clicked, this, &MainWindow::createGpsFollower);
}

MainWindow::~MainWindow() {
    delete ui;
}

// Función auxiliar
QString MainWindow::promptFor(const QString& title, const QString& label) {
    bool ok;
    QString text = QInputDialog::getText(this, title, label, QLineEdit::Normal, "", &ok);
    return ok ? text : QString();
}

// VIDEO
void MainWindow::createVideoPublisher() {
    QString name = promptFor("Nombre Publicador", "Ingrese el nombre del publicador:");
    if (name.isEmpty()) return;

    QString topic = promptFor("Tópico", "Ingrese el nombre del tópico:");
    if (topic.isEmpty()) return;

    auto* vp = new VideoPublisher(broker, name, topic);
    publisherLayout->addWidget(vp);
}

void MainWindow::createVideoFollower() {
    QString name = promptFor("Nombre Suscriptor", "Ingrese el nombre del suscriptor:");
    if (name.isEmpty()) return;

    QString topic = promptFor("Tópico", "Ingrese el nombre del tópico a suscribirse:");
    if (topic.isEmpty()) return;

    auto* vf = new VideoFollower(name, topic);
    if (broker->subscribe(vf)) {
        subscriberLayout->addWidget(vf);
    } else {
        delete vf;
        QMessageBox::warning(this, "Error", "El tópico no existe.");
    }
}

// GPS
void MainWindow::createGpsPublisher() {
    QString name = promptFor("Nombre Publicador", "Ingrese el nombre del publicador GPS:");
    if (name.isEmpty()) return;

    QString topic = promptFor("Tópico", "Ingrese el nombre del tópico:");
    if (topic.isEmpty()) return;

    auto* gp = new GPSCarPublisher(broker, name, topic);
    publisherLayout->addWidget(gp);
}

void MainWindow::createGpsFollower() {
    QString name = promptFor("Nombre Suscriptor", "Ingrese el nombre del seguidor GPS:");
    if (name.isEmpty()) return;

    QString topic = promptFor("Tópico", "Ingrese el nombre del tópico a suscribirse:");
    if (topic.isEmpty()) return;

    auto* gf = new GPSFollower(name, topic);
    if (broker->subscribe(gf)) {
        subscriberLayout->addWidget(new QLabel("GPSFollower activo: " + name));
    } else {
        delete gf;
        QMessageBox::warning(this, "Error", "El tópico no existe.");
    }
}
