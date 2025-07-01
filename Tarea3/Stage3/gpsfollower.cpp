#include "gpsfollower.h"
#include <QVBoxLayout>
#include <QLabel>
#include <QStringList>
#include <QDebug>

GPSFollower::GPSFollower(const QString& name, const QString& topicName, QWidget* parent)
    : QWidget(parent), Subscriber(name, topicName)
{
    QVBoxLayout* layout = new QVBoxLayout(this);

    QLabel* title = new QLabel("Seguidor GPS: " + name);
    timeLabel = new QLabel("Tiempo: --");
    xLabel = new QLabel("X: --");
    yLabel = new QLabel("Y: --");

    layout->addWidget(title);
    layout->addWidget(timeLabel);
    layout->addWidget(xLabel);
    layout->addWidget(yLabel);

    this->setLayout(layout);
    this->setWindowTitle("GPSFollower - " + name);
    this->resize(300, 150);
    this->show();
}

void GPSFollower::update(const QString& message) {
    QStringList parts = message.split(' ', Qt::SkipEmptyParts);
    if (parts.size() != 3) return;

    QString tiempo = parts[0];
    QString x = parts[1];
    QString y = parts[2];

    timeLabel->setText("Tiempo: " + tiempo);
    xLabel->setText("X: " + x);
    yLabel->setText("Y: " + y);
}
