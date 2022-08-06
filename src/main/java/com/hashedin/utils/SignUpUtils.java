package com.hashedin.utils;

import java.util.UUID;

public class SignUpUtils {

    public static String generateUniqueId()
    {
        return UUID.randomUUID().toString();
    }


}
