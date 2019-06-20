import os
import sys
import numpy as np

class ImageAnalyzer:

    __instance = None

    @staticmethod
    def getInstance():
        """ Static access method. """
        if ImageAnalyzer.__instance == None:
            ImageAnalyzer()
        return ImageAnalyzer.__instance 

    def __init(self):
        ROOT_DIR = os.path.abspath('./libs/')

        # Import Mask RCNN
        sys.path.append(ROOT_DIR)  # To find local version of the library
        import libs.mrcnn.utils as utils
        import libs.mrcnn.model as modellib
        
        sys.path.append(os.path.join(ROOT_DIR, './libs/coco/'))  # To find local version
        import coco
        
        MODEL_DIR = os.path.join(ROOT_DIR, "logs")
        COCO_MODEL_PATH = os.path.join(ROOT_DIR, "mask_rcnn_coco.h5")
        if not os.path.exists(COCO_MODEL_PATH):
            utils.download_trained_weights(COCO_MODEL_PATH)
        
        from inference_config import InferenceConfig
        config = InferenceConfig()
        self.model = modellib.MaskRCNN(mode="inference", model_dir=MODEL_DIR, config=config)
        self.model.load_weights(COCO_MODEL_PATH, by_name=True)

    def __init__(self):
        """ Virtually private constructor. """
        if ImageAnalyzer.__instance != None:
            raise Exception("This class is a singleton!")
        else:
            self.__init()
            ImageAnalyzer.__instance = self

    def extractCars(self, image):
        results = self.model.detect([image], verbose=1)
        r = results[0]
        car_boxes = self.__getCarBoxes(r['rois'], r['class_ids'])
        return car_boxes


    # ------ PRIVATE HELPER -----------------
    def __getCarBoxes(self, boxes, class_ids):
        car_boxes = []

        for i, box in enumerate(boxes):
            # If the detected object isn't a car / truck, skip it
            if class_ids[i] in [3, 8, 6]:
                car_boxes.append(box)

        return np.array(car_boxes)

