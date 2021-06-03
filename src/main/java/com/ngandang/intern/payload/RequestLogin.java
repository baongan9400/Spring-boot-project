package com.ngandang.intern.payload;

import javax.validation.constraints.NotBlank;

public class RequestLogin {
        @NotBlank
        private String username;

        @NotBlank
        private String password;

        public RequestLogin(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
