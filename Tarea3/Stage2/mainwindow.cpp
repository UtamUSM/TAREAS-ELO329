#include "mainwindow.h"
#include "ui_mainwindow.h"

#include "broker.h"
#include "videopublisher.h"
#include "videofollower.h"

#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QPushButton>
#include <QInputDialog>
#include <QMessageBox>
#include <QWidget>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent),
    ui(new Ui::MainWindow),
    broker(new Broker)
{
    ui->setupUi(this);

    // Contenedor principal sin usar .ui
    centralWidgetContainer = new QWidget(this);
    this->setCentralWidget(centralWidgetContainer);

    // Layouts
    QHBoxLayout* mainLayout = new QHBoxLayout(centralWidgetContainer);
    QVBoxLayout* controlLayout = new QVBoxLayout();
    publisherLayout = new QVBoxLayout();
    subscriberLayout = new QVBoxLayout();

    QPushButton* addVideoPubBtn = new QPushButton("Agregar Video Publisher");
    QPushButton* addVideoSubBtn = new QPushButton("Agregar Video Follower");

    controlLayout->addWidget(addVideoPubBtn);
    controlLayout->addWidget(addVideoSubBtn);
    controlLayout->addStretch();

    mainLayout->addLayout(controlLayout, 1);
    mainLayout->addLayout(publisherLayout, 2);
    mainLayout->addLayout(subscriberLayout, 2);
    centralWidgetContainer->setLayout(mainLayout);

    connect(addVideoPubBtn, &QPushButton::clicked, this, &MainWindow::createVideoPublisher);
    connect(addVideoSubBtn, &QPushButton::clicked, this, &MainWindow::createVideoFollower);
}

MainWindow::~MainWindow() {
    delete ui;
}

QString MainWindow::promptFor(const QString& title, const QString& label) {
    bool ok;
    QString text = QInputDialog::getText(this, title, label, QLineEdit::Normal, "", &ok);
    return ok ? text : QString();
}

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
        QMessageBox::warning(this, "Error", "El tópico no existe.");
        delete vf;
    }
}
