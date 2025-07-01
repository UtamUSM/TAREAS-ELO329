#include "videofollower.h"
#include <QPushButton>
#include <QVBoxLayout>
#include <QMediaPlayer>
#include <QVideoWidget>
#include <QAudioOutput>
#include <QUrl>
#include <QSlider>

VideoFollower::VideoFollower(const QString& name, const QString& topicName, QWidget* parent)
    : QWidget(parent), Subscriber(name, topicName)
{
    QVBoxLayout* layout = new QVBoxLayout(this);
    button = new QPushButton("Sin URL", this);
    button->setEnabled(false);
    layout->addWidget(button);

    connect(button, &QPushButton::clicked, this, &VideoFollower::onButtonClicked);
}

void VideoFollower::update(const QString& message) {
    currentUrl = message;
    button->setText(message);
    button->setEnabled(true);
}


class VideoPlayerWindow : public QWidget {
public:
    VideoPlayerWindow(QMediaPlayer* player, QAudioOutput* audio, QWidget* parent = nullptr)
        : QWidget(parent), player(player), audio(audio) {}

protected:
    void closeEvent(QCloseEvent* event) override {
        if (player) {
            player->stop();
            delete player;
            player = nullptr;
        }
        if (audio) {
            delete audio;
            audio = nullptr;
        }
        QWidget::closeEvent(event);
    }

private:
    QMediaPlayer* player;
    QAudioOutput* audio;
};

void VideoFollower::onButtonClicked() {
    if (currentUrl.isEmpty()) return;

    QMediaPlayer* player = new QMediaPlayer;
    QAudioOutput* audio = new QAudioOutput;

    VideoPlayerWindow* playerWindow = new VideoPlayerWindow(player, audio);
    playerWindow->setWindowTitle("Reproductor de Video");

    QVBoxLayout* layout = new QVBoxLayout(playerWindow);

    QVideoWidget* videoWidget = new QVideoWidget(playerWindow);
    QSlider* volumeSlider = new QSlider(Qt::Horizontal, playerWindow);

    audio->setVolume(0.8);
    volumeSlider->setRange(0, 100);
    volumeSlider->setValue(80);

    player->setVideoOutput(videoWidget);
    player->setAudioOutput(audio);

    connect(volumeSlider, &QSlider::valueChanged, [audio](int value) {
        audio->setVolume(value / 100.0);
    });

    QUrl videoUrl(currentUrl);
    if (videoUrl.isLocalFile() || videoUrl.scheme().startsWith("http")) {
        player->setSource(videoUrl);
        player->play();

        layout->addWidget(videoWidget);
        layout->addWidget(volumeSlider);
        playerWindow->setLayout(layout);
        playerWindow->resize(800, 650);
        playerWindow->show();
    }
}


