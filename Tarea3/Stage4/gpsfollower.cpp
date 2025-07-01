#include "gpsfollower.h"
#include <QVBoxLayout>
#include <QLabel>
#include <QStringList>
#include <QPainter>
#include <QPaintEvent>

GPSFollower::GPSFollower(const QString& name, const QString& topicName, QWidget* parent)
    : QWidget(parent), Subscriber(name, topicName), currentX(0), currentY(0), scale(5.0)
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
    layout->addStretch(); // empuja el dibujo hacia arriba

    this->setLayout(layout);
    this->setWindowTitle("GPSFollower - " + name);
    this->resize(400, 400);
    this->show();
}

void GPSFollower::update(const QString& message) {
    QStringList parts = message.split(' ', Qt::SkipEmptyParts);
    if (parts.size() != 3) return;

    QString tiempo = parts[0];
    currentX = parts[1].toDouble();
    currentY = parts[2].toDouble();

    timeLabel->setText("Tiempo: " + tiempo);
    xLabel->setText("X: " + QString::number(currentX, 'f', 2));
    yLabel->setText("Y: " + QString::number(currentY, 'f', 2));

    QWidget::update();
}

void GPSFollower::paintEvent(QPaintEvent* event) {
    QWidget::paintEvent(event);

    QPainter painter(this);
    painter.setRenderHint(QPainter::Antialiasing);
    painter.setBrush(Qt::blue);
    painter.setPen(Qt::black);

    // Posición transformada al área visible (escalar para que no se salga)
    int cx = static_cast<int>(currentX * scale);
    int cy = static_cast<int>(currentY * scale);

    // Círculo de radio fijo, centrado en la posición transformada
    painter.drawEllipse(cx, cy, 20, 20);
}
