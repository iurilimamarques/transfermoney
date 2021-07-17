package com.iurilima.transfermoney.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class TimeoutConfigFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        ScheduledExecutorService timeoutPool = Executors.newScheduledThreadPool(10);

        AtomicBoolean completed = new AtomicBoolean(false);
        Thread requestHandlingThread = Thread.currentThread();

        ScheduledFuture<?> timeout = timeoutPool.schedule(() -> {
            if (completed.compareAndSet(false, true))
                requestHandlingThread.interrupt();
        }, 10, TimeUnit.SECONDS);

        try {
            filterChain.doFilter(request, response);
            timeout.cancel(false);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            completed.set(true);
        }
    }

    public Runnable scheduler(AtomicBoolean completed, Thread requestHandlingThread) {
        return () -> {
            if (completed.compareAndSet(false, true))
                requestHandlingThread.interrupt();
        };
    }
}
