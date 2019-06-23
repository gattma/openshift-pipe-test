import cv2

class ImageClient:

    def getImageFromWebcam(self, url):
        return self.captureImage(url)

    def captureImage(self, url):
        vidcap = cv2.VideoCapture(str(url))
        success,image = vidcap.read()
        if not success:
            raise Exception("Failed to capture image from %s" % url)
        return image
