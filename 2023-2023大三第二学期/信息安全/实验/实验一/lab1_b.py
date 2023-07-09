

# 将每个字符转换为对应的 ASCII 码，并将结果格式化为二进制字符串
def str_to_binary(s):
    binary_list = [format(ord(c), '08b') for c in s]
    binary_str = ''.join(binary_list)
    return binary_str

# 将二进制字符串转换为原字符串
def binary_to_str(s):
    return ''.join([chr(i) for i in [int(b, 2) for b in [s[i:i+8] for i in range(0, len(s), 8)]]])

# 非线性映射函数
def f(right, key):
    result = int(right, 2) ^ int(key, 2)
    return result

# Feistel网络一轮加密函数
def feistel_encrypt_round(left, right, key):
    new_left = right
    new_right = int(left, 2) ^ f(right, key)
    new_right = format(new_right, f'0{len(right)}b')
    return new_left, new_right

# Feistel网络一轮解密函数
def feistel_decrypt_round(left, right, key):
    new_right = left
    new_left = int(right, 2) ^ f(left, key)
    new_left = format(new_left, f'0{len(right)}b')
    return new_left, new_right

# Feistel网络加密函数
def feistel_encrypt(data, key):
    # 将数据分为左右两部分
    left = data[:len(data)//2]
    right = data[len(data)//2:]
    # 进行 16 轮加密
    for i in range(16):
        left, right = feistel_encrypt_round(left, right, key)
        key = key[15:] + key[:15]
    # 拼接
    return left + right

# Feistel网络解密函数
def feistel_decrypt(data, key):
    # 将数据分为左右两部分
    left = data[:len(data)//2]
    right = data[len(data)//2:]
    # 进行 16 轮解密
    for i in range(16):
        key = key[1:] + key[:1]
        left, right = feistel_decrypt_round(left, right, key)
    # 拼接
    return left + right


if __name__ == '__main__':
    # 原字符串
    text = "CQUINFORMATIONSECURITYEXP"
    # 将原字符串转换为二进制字符串
    explicit_data = str_to_binary(text)
    # 密钥
    key = "1111111111111110"
    # 加密
    secret_data = feistel_encrypt(explicit_data, key)
    # 解密
    decrypt_data = feistel_decrypt(secret_data, key)
    # 将解密后的二进制字符串转换为原字符串
    decrypt_text = binary_to_str(decrypt_data)
    # 打印结果
    print(f'原字符串：{text}')
    print(f'加密后的二进制字符串：{secret_data}')
    print(f'解密后的二进制字符串：{decrypt_data}')
    print(f'解密后的字符串：{decrypt_text}')