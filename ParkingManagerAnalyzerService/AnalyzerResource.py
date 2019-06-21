from flask import Flask, request, jsonify
from flask_restful import Resource, Api, reqparse
from AnalyzerService import AnalyzerService

app = Flask(__name__)

@app.route('/analyze/health')
def healtCheck():
    return "OK"

service = AnalyzerService()

@app.route('/analyze')
def analyzeUrl():
    url = __extractUrl()
    car_boxes = service.analyze(url)
    
    parkingplace = {}
    parkingplace["webcamUrl"] = url
    parkingplace["parkingSpaces"] = []
    tmp = {}
    for box in car_boxes:
        tmp["y1"] = int(box[0])
        tmp["x1"] = int(box[1])
        tmp["y2"] = int(box[2])
        tmp["x2"] = int(box[3])
        parkingplace["parkingSpaces"].append(tmp)

    app.logger.info(parkingplace)

    return jsonify(parkingplace)

def __extractUrl():
    parser = reqparse.RequestParser()
    parser.add_argument('url', store_missing=False)
    args = parser.parse_args()
    return args['url']

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')