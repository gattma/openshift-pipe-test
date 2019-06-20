from image_analyzer import ImageAnalyzer
from image_client import ImageClient

class AnalyzerService:

    image_client = ImageClient()

    def analyze(self, url):
        image = self.__takePicture(url)
        return self.__analyzeImage(image)
        
    # --------- PRIVATE HELPER --------------
    def __takePicture(self, url):
        image = self.image_client.getImageFromWebcam(url, app)
        return image

    def __analyzeImage(self, image):
        image_analyzer = ImageAnalyzer.getInstance()
        return image_analyzer.extractCars(image)
