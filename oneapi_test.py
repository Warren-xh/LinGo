import requests


ONE_API_TOKEN = 'sk-m64JA72WnVbOj5CQ7cFc4cFf05Df4eCe93B2F54aC0F6E3Cf'


API_URL = 'http://localhost:3000/v1/chat/completions'


PROMPT_TEMPLATE = "假设你是一名超市的消费者，正打算通过搜索____来查找商品的信息，但是无法精准搜索到，请你生成五个用于模糊搜索的关键词，记住必须严格遵循以下三个原则,1.只输出关键词不需要任何解释，2.如果实在没有这样的词，就输出输入的信息"

def create_custom_prompt(keyword):
    return PROMPT_TEMPLATE.replace("____", keyword)

def get_prediction(prompt):
    headers = {
        'Authorization': f'Bearer {ONE_API_TOKEN}',
        'Content-Type': 'application/json'
    }
    
    data = {
        'model': 'ERNIE-4.0-8K',  
        'messages': [
            {
                'role': 'user',
                'content': prompt
            }
        ],
        'temperature': 0.7
    }
    
    response = requests.post(API_URL, headers=headers, json=data)
    
    if response.status_code == 200:
        return response.json()
    else:
        return response.status_code, response.text

if __name__ == "__main__":
    keyword = input("Please enter the product name：")
    custom_prompt = create_custom_prompt(keyword)
    prediction = get_prediction(custom_prompt)
    print("LinGo：", prediction)
    

